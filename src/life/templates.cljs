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

(defn label [n]
  ($ (str "<label for=\"" n "\">" n "</label>")))

(defn check [n]
  ($ (str "<input id=\"" n "\" type=\"checkbox\" name=\""
          n "\" class=\"" n "\">")))

(defn checkbox [n]
  (let [$elem ($ (str "<div></div>"))]
    (j/append $elem (check n))
    (j/append $elem (label n))
    $elem))
