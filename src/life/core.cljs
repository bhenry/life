(ns life.core
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]
            [life.bj :as bj]
            [life.game :as g]
            [life.templates :as t]))

(defn ticker [tick]
  (js/setTimeout (fn []
                   (b/push tick :tick)
                   (ticker tick)) 1000))

(defn ^:export main []
  (let [$content ($ "#content")
        game (g/game 20 20)
        _ (j/html $content (:$elem game))
        _ (j/append $content (t/button "step"))
        _ (j/append $content (t/checkbox "auto"))
        step (bj/clickE ($ ".step" $content))
        auto (bj/check-box-value ($ ".auto" $content))
        tick (b/bus)]
    (ticker tick)
    (b/plug (:step game)
            (-> (b/combine-with tick auto list)
                (b/filter second)))
    (b/plug (:step game) step)))
