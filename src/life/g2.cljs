(ns life.g2
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]
            [life.bj :as bj]
            [life.templates :as t]))

(defn render [$c]
  (fn [v]
    (if v
      (j/add-class $c "alive")
      (j/remove-class $c "alive"))))

(defn add-life [point]
  (fn [world]
    (set (cons point world))))

(defn remove-life [point]
  (fn [world]
    (set (remove #{point} world))))

(defn toggle [p w]
  (fn [_]
    (if ((set (bj/get-value w)) p)
      (bj/modify w (remove-life p))
      (bj/modify w (add-life p)))))

(defn cell [vw w p t]
  (let [$c (t/table-cell)
        click (bj/clickE $c)
        changes (-> w
                    b/changes
                    (b/map #((set %) p))
                    b/skip-duplicates)]
    (b/on-value changes (render $c))
    (b/on-value click (toggle p w))
    {:$elem $c}))

(defn get-neighbors [p]
  ((apply juxt
          (for [a [-1 0 1]
                b  [-1 0 1]
                :when (not= [0 0] [a b])]
            (fn [[x y]] [(+ x a) (+ y b)]))) p))

(defn sustain [w]
  (remove nil?
          (for [p w
                :let [neighbors (filter (set w) (get-neighbors p))]]
            (condp > (count neighbors)
              2 nil
              4 p
              9 nil))))

(defn reproduce [w]
  (remove nil?
          (for [p (set (mapcat get-neighbors w))
                :let [nneighbors (filter (set w) (get-neighbors p))]]
            (if (= 3 (count nneighbors))
              p
              nil))))

(defn iteration [w]
  (set (concat (sustain w) (reproduce w))))

(defn build [h w]
  (into {}
        (for [x (range w)
              y (range h)
              :let [p [x y]]]
          [p (b/bus)])))

(defn game [h w]
  (let [visible-world (build h w)
        world (bj/model #{})
        $t (t/table)
        tick (b/bus)]
    (doseq [y (range h)
            :let [$r (t/table-row)]]
      (j/append $t $r)
      (doseq [x (range w)
              :let [c (cell visible-world
                            world
                            [x y]
                            tick)
                    $c (:$elem c)]]
        (j/append $r $c)))
    (b/on-value tick #(bj/modify world iteration))
    {:$elem $t
     :tick tick
     :world world}))
