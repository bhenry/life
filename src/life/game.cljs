(ns life.game
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]
            [life.bj :as bj]
            [life.templates :as t]))

(defn add-life [point]
  (fn [world]
    (set (cons point world))))

(defn remove-life [point]
  (fn [world]
    (set (remove #{point} world))))

(defn bind-click [world point state click-stream]
  (bj/add-source state
                 (-> click-stream
                     (b/map #(not (bj/get-value state)))))
  (-> state
      b/changes
      (b/on-value
       #(if %
          (bj/modify world (add-life point))
          (bj/modify world (remove-life point))))))

(defn bind-render [world point state $elem tick]
  (-> state
      b/changes
      (b/on-value
       #(if %
          (j/add-class $elem "alive")
          (j/remove-class $elem "alive"))))
  (-> world
      b/changes
      (b/filter #((set %) point))))

(defn cell [world point tick]
  (let [$c (t/table-cell)
        state (bj/model nil)
        click-stream (bj/clickE $c)]
    (bind-click world point state click-stream)
    (bind-render world point state $c tick)
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

(defn game [h w world]
  (let [$t (t/table)
        world (bj/model (set world))
        tick (b/bus)]
    (doseq [y (range h)
            :let [$r (t/table-row)]]
      (j/append $t $r)
      (doseq [x (range w)
              :let [c (cell world [x y] tick)
                    $c (:$elem c)]]
        (j/append $r $c)))
    (-> tick
        (b/on-value
         (fn [_] (bj/modify world iteration))))
    {:$elem $t
     :world world
     :tick tick}))
