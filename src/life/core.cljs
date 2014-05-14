(ns life.core
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]))

(defn $table []
  ($ "<dic></div>"))

(defn ^:export main []
  (let [$content ($ "#content")]
    (j/html $content
            (j/append ($table) "HI"))))
