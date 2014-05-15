(ns life.bj)

(defn clickE
  ([$elem]
     (.clickE $elem))
  ([$elem selector]
     (.clickE $elem selector)))

(defn model
  ([]
     (js/Bacon.$.Model))
  ([v]
     (js/Bacon.$.Model (clj->js v))))

(defn add-source [model stream]
  (.addSource model stream))

(defn get-value [model]
  (js->clj (.get model)))

(defn set-value [model value]
  (.set model (clj->js value)))

(defn modify [model f]
  (.modify model f))

(def check-box-value js/Bacon.$.checkBoxValue)
