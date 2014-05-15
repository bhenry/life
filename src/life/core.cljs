(ns life.core
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]
            [life.bj :as bj]
            [life.game :as g]
            [life.templates :as t]))

(defn ^:export main []
  (let [$content ($ "#content")
        game (g/game 20 20)]
    (j/html $content (:$elem game))
    (j/append $content (t/button "step"))
    (b/plug (:tick game) (bj/clickE ($ ".step" $content)))))
