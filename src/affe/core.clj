(ns affe.core
  (:require [clojure-csv.core :refer [parse-csv]]
            [clojure.string :as string]
            [datomic.api :as d]
            [clojure.java.io :as io])
  (:import datomic.Util))

(defn take-csv
  "Takes file name and reads data."
  [fname]
  (with-open [file (io/reader fname)]
    (parse-csv (slurp file))))


(defn scratch-conn
  "Create a connection to an anonymous, in-memory database."
  []
  (let [uri (str "datomic:mem://" (d/squuid))]
    (d/delete-database uri)
    (d/create-database uri)
    (d/connect uri)))

(defn init-datomic [conn]
  (doseq [txd (Util/readAll (io/reader "data/schema.edn"))]
    (d/transact conn txd)))

(defn import-to-datomic [conn data]
  (doseq [entry data]
    (d/transact conn [(assoc entry :db/id (d/tempid :db.part/user))])))


(def gdp-data
  (let [raw-data (take-csv "data/QNA.csv")
      columns (first raw-data)]
  (->> raw-data
       rest
       (map #(zipmap columns %))
       (map #(select-keys % ["Country" "TIME" "Value"]))
       (map #(let [parsed-time (string/split (get % "TIME") #"-")]
               {:gdp/country (get % "Country")
                :gdp/year (read-string (first parsed-time))
                :gdp/quarter (read-string (re-find #"[0-9]+" (second parsed-time)))
                :gdp/value (read-string (get % "Value"))})))))





(comment
  (def conn (scratch-conn))

  (init-datomic conn)

  (import-to-datomic conn (take 100 gdp-data))

  (d/q '[:find ?r
         :in $ ?c
         :where
         [?r :gdp/country ?c]]
       (d/db conn)
       "Argentinia")


  )
