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

(defn bind-click [world point click-stream state]
  (bj/add-source state
                 (-> click-stream
                     (b/map #(not (bj/get-value state)))))
  (-> state
      b/changes
      (b/on-value
       #(if %
          (bj/modify world (add-life point))
          (bj/modify world (remove-life point))))))

(defn bind-render [state $elem]
  (-> state
      b/changes
      (b/on-value
       #(if %
          (j/add-class $elem "alive")
          (j/remove-class $elem "alive")))))

(defn cell [world point]
  (let [$c (t/table-cell)
        state (bj/model nil)
        click-stream (bj/clickE $c)]
    (bind-click world point click-stream state)
    (bind-render state $c)
    {:$elem $c
     :state state
     :clicked click-stream}))

(defn game [h w]
  (let [$t (t/table)
        world (bj/model [])]
    (doseq [y (range h)
            :let [$r (t/table-row)]]
      (j/append $t $r)
      (doseq [x (range w)
              :let [c (cell world [x y])]]
        (j/append $r (:$elem c))))
    {:$elem $t
     :world world}))
