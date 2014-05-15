(ns life.templates
  (:require [jayq.core :as j :refer [$]]))

(defn table []
  ($ "<table></table>"))

(defn table-row []
  ($ "<tr></tr>"))

(defn table-cell []
  ($ "<td></td>"))

(defn button [n]
  ($ (str "<button class=\"" n "\">" n "</button>")))
