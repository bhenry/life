(ns life.core
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]
            [life.bj :as bj]
            [life.game :as g]
            [life.templates :as t]))

(defn ticker [tick]
  (js/setTimeout (fn []
                   (b/push tick :tick)
                   (ticker tick)) 2000))

(defn draw-page [$content]
  (let [$menu (t/controls)]
    (j/append $content $menu)
    (j/append $menu (t/button "clear"))
    (j/append $menu (t/button "rewind"))
    (j/append $menu (t/button "step"))
    (j/append $menu (t/checkbox "auto"))
    (j/after $menu (t/hr))
    (j/append $content (t/div "game"))))

(defn draw-game [$content game]
  (j/html ($ ".game" $content) (:$table game)))

(defn ^:export main []
  (let [$content ($ "#content")
        game (g/game 30 50
                     #_ #{[20 20] [20 21] [21 20] [21 21]})
        _ (draw-page $content)
        _ (draw-game $content game)
        clear (bj/clickE ($ ".clear" $content))
        rewind (bj/clickE ($ ".rewind" $content))
        step (bj/clickE ($ ".step" $content))
        auto (bj/check-box-value ($ ".auto" $content))
        tick (b/bus)
        timer (ticker tick)]
    (b/plug (:clear game) clear)
    (b/plug (:rewind game) rewind)
    (b/plug (:step game) step)
    (b/plug (:step game)
            (-> (b/combine-with tick auto list)
                (b/filter second)))
    (bj/add-source auto (-> (b/changes (:world game))
                            (b/filter empty?)
                            (b/map false)))))
