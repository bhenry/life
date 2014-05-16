(ns life.core
  (:require [jayq.core :as j :refer [$]]
            [yolk.bacon :as b]
            [life.bj :as bj]
            [life.game :as g]
            [life.templates :as t]))

(def seed
  #{[8 13] [9 13] [4 11] [6 13] [8 14] [11 14] [4 12] [5 13]
    [4 13] [14 14] [12 13] [15 14] [12 14] [14 13] [38 13]
    [37 13] [34 13] [35 14] [4 14] [34 14] [5 14] [32 13]
    [6 14] [27 12] [29 13] [27 11] [28 13] [31 14] [29 14]
    [37 14] [24 14] [27 14] [27 13] [41 14] [18 13] [20 14]
    [40 13] [18 14] [21 14] [17 14] [40 15] [17 13] [18 12]
    [23 14] [23 13] [21 13] [20 13]})

(defn ticker [tick]
  (js/setTimeout (fn []
                   (b/push tick :tick)
                   (ticker tick)) 2000))

(defn draw-page [$content]
  (let [$menu (t/div "controls")
        $util (t/div "utilities")]
    (j/append $content $menu)
    (j/append $menu (t/button "clear"))
    (j/append $menu (t/button "rewind"))
    (j/append $menu (t/button "step"))
    (j/append $menu (t/checkbox "auto"))
    (j/append $content (t/hr))
    (j/append $content (t/div "game"))
    (j/append $content (t/hr))
    (j/append $content $util)
    (j/append $util (t/button "print"))))

(defn draw-game [$content game]
  (j/html ($ ".game" $content) (:$table game)))

(defn ^:export main []
  (let [$content ($ "#content")
        game (g/game 30 50 seed)
        _ (draw-page $content)
        _ (draw-game $content game)
        clear (bj/clickE ($ ".clear" $content))
        rewind (bj/clickE ($ ".rewind" $content))
        step (bj/clickE ($ ".step" $content))
        auto (bj/check-box-value ($ ".auto" $content))
        print (bj/clickE ($ ".print" $content))
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
                            (b/map false)))
    (bj/add-source auto (b/map rewind false))
    (b/on-value print #(js/console.log (pr-str (bj/get-value (:world game)))))))
