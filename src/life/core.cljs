(ns life.core
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]
            [life.bj :as bj]
            [life.g2 :as g]
            [life.input :as i]
            [life.templates :as t]))

(defn timer [tick]
  (js/setTimeout #(do (b/push tick :tick)
                      (timer tick))
                 1000))

(defn ^:export main []
  (let [$content ($ "#content")
        game (g/game 20 20)]
    #_(timer (:tick game))
    
    (j/html $content (:$elem game))
    (j/append $content (t/button "step"))
    (b/plug (:tick game) (bj/clickE ($ ".step" $content)))))
