(ns life.core
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]
            [life.templates :as t]))

(defn ^:export main []
  (let [$content ($ "#content")]
    (j/html $content (t/game 9 9))))
