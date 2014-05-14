(ns life.core
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]
            [life.game :as g]
            [life.input :as i]
            [life.templates :as t]))


(defn ^:export main []
  (let [$content ($ "#content")
        game (g/game 20 20)]
    (j/html $content (:$elem game))))
