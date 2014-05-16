(ns life.game
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

(defn cell [w p]
  (let [$c (t/table-cell)
        click (bj/clickE $c)
        changes (-> w
                    b/changes
                    (b/map #((set %) p))
                    b/skip-duplicates)]
    (b/on-value changes (render $c))
    (b/on-value click (toggle p w))
    {:$cell $c}))

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

(defn quantum-leap [h]
  (fn [w]
    (cond (empty? (first h)) w
          :else (first h))))

(defn write-history [w]
  (fn [h]
    (cond (= w (first h)) h
          (empty? w) h
          :else (take 100 (cons w h)))))

(defn time-travel [history world dir]
  (let [h (bj/get-value history)
        w (bj/get-value world)]
    (if (neg? dir)
      (do (bj/modify history (constantly (rest h)))
          (bj/modify world (quantum-leap h)))
      (do (bj/modify world iteration)
          (bj/modify history (write-history w))))))

(defn game [h w & [seed]]
  (let [world (bj/model #{})
        history (bj/model [])
        $t (t/table)
        rewind (b/bus)
        step (b/bus)
        clear (b/bus)]
    (doseq [y (range h)
            :let [$r (t/table-row)]]
      (j/append $t $r)
      (doseq [x (range w)
              :let [c (cell world [x y])
                    $c (:$cell c)]]
        (j/append $r $c)))
    (b/on-value rewind #(time-travel history world -1))
    (b/on-value step #(time-travel history world 1))
    (b/on-value clear #(bj/modify world (constantly #{})))
    (when seed (bj/modify world (constantly seed)))
    {:$table $t
     :step step
     :clear clear
     :world world
     :history history
     :rewind rewind}))
