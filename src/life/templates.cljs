(ns life.templates
  (:require [jayq.core :as j :refer [$]]))

(defn table []
  ($ "<table></table>"))

(defn table-row []
  ($ "<tr></tr>"))

(defn table-cell [p]
  ($ (str "<td data-coords=\"" p "\"></td>")))

(defn neighbor []
  (let [n ($ "<div></div>")]
    (j/add-class n "neighbor")
    n))

(defn game [h w]
  (let [$t (table)]
    (doseq [y (range h)
            :let [$r (table-row)]]
      (j/append $t $r)
      (doseq [x (range w)
              :let [$c (table-cell [x y])]]
        (j/append $r $c)))
    $t))
