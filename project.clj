(defproject affe "0.1.0-SNAPSHOT"

  :description "Gorilla examples"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [com.taoensso/timbre "3.3.1"]

                 [net.polyc0l0r/konserve "0.2.3"]
                 [net.polyc0l0r/geschichte "0.1.0-SNAPSHOT"]
                 [com.datomic/datomic-free "0.9.5130"]
                 [geschichte-gorilla "0.1.0-SNAPSHOT"]

                 [gg4clj "0.1.0"]
                 [incanter "1.9.0"]
                 [aprint "0.1.3"]]

  :plugins [[lein-gorilla "0.3.4"]])
