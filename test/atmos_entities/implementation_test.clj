(ns atmos-entities.implementation-test
  (:require [clojure.test :refer :all]
            [atmos-entities.core :refer [add-entity ->Entity]]
            [atmos-entities.implementation :refer [init-persistance db-definition]]))

(def ^:private db (db-definition {:host     "transportation-dev-db.c4r6yc5ou9f3.us-east-1.rds.amazonaws.com"
                                  :db       "atmos-entities"
                                  :user     "developer"
                                  :password "12345678"}))

(init-persistance db)

(def mock-entity (->Entity 0 "MOCK-USER" "Mock" "User"))


(deftest data-insertion
  (testing "Data insertion"
    (is (true? (add-entity mock-entity)))))
