(ns atmos-entities.implementation-test
  (:require [clojure.test :refer :all]
            [atmos-entities.core :refer :all]
            [atmos-entities.implementation :refer [init-persistence defpersistence]]))

(def mock-db {:aws       {:host     "transportation-dev-db.c4r6yc5ou9f3.us-east-1.rds.amazonaws.com"
                          :db       "atmos-entities"
                          :user     ""
                          :password ""}
              :localhost {:host     "localhost"
                          :db       "atmos-entities"
                          :user     "root"
                          :password ""}})

(-> mock-db :localhost defpersistence init-persistence)

(def mock-entity {:type     "MOCK-USER"
                  :name     "Mock"
                  :lastName "User"})


(deftest data-insertion
  (testing "Data insertion"
    (is (true? (add-entity mock-entity)))))

(deftest data-selection
  (testing "Data selection"
    (is (= 1 (-> 1 get-entity :id)))))

(deftest data-deletion
  (testing "Data deletion"
    (is (true? (remove-entity 15)))))
