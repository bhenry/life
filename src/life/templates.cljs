(ns life.templates
  (:require [jayq.core :as j :refer [$]]))

(defn option [opts & ks]
  (if (string? opts)
    opts
    (first (remove nil? ((apply juxt ks) opts)))))

(defn div [& [opts]]
  (let [$d ($ (str "<div>"))
        class (option opts :class :name)]
    (when class
      (j/add-class $d class))
    
    $d))

(defn hr []
  ($ (str "<hr>")))

(defn table []
  ($ "<table>"))

(defn table-row []
  ($ "<tr>"))

(defn table-cell []
  ($ "<td>"))

(defn button [n]
  (let [$b ($ (str "<button>"))]
    (j/add-class $b n)
    (j/html $b n)
    $b))

(defn label [n]
  (let [$l ($ (str "<label>"))]
    (j/attr $l "for" n)
    (j/html $l n)
    $l))

(defn check [n]
  (let [$i ($ "<input>")]
    (j/attr $i "type" "checkbox")
    (j/attr $i "name" n)
    (j/attr $i "id" n)
    (j/add-class $i n)
    $i))

(defn checkbox [n]
  (let [$elem (div)]
    (j/append $elem (check n))
    (j/append $elem (label n))
    $elem))

(defn controls []
  ($ "<div class='controls'><div/>"))
