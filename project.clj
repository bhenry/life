(defproject life "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2197"]
                 [jayq "2.5.1"]
                 [yolk "0.9.0"]
                 [yolk-jquery "0.6.0"]]
  :plugins [[lein-cljsbuild "0.3.0"]]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :compiler {:output-to "resources/life.js"
                                   :output-dir "out"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map true}}]})
