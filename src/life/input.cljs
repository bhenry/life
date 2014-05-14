(ns life.input
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]))

(defn- target [e]
  (-> ($ (aget e "target"))
      (j/closest "td")))

