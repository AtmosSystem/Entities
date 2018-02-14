(ns atmos-entities.core-test
  (:require [clojure.test :refer :all]
            [atmos-entities.core :refer :all]
            [atmos-entities.implementation :refer [init-persistence defpersistence]]))

(def mock-db {:aws       {:host     "transportation-dev-db.c4r6yc5ou9f3.us-east-1.rds.amazonaws.com"
                          :db       "atmos-entities"
                          :user     "developer"
                          :password "12345678"}

              :localhost {:host     "localhost"
                          :db       "atmos-entities"
                          :user     "root"
                          :password ""}})

(-> mock-db :aws defpersistence init-persistence)


(deftest repository-testing
  (let [mock-entity {:type     "MOCK-USER"
                     :name     "Mock"
                     :lastName "User"}
        test-id 6
        mock-update-entity {:id       test-id
                            :lastName "User edited"}
        id-inserted (add-entity mock-entity)
        id-to-remove id-inserted]
    (testing "Insert data"
      (is (integer? id-inserted)))
    (testing "Retrieve data"
      (is (= test-id (-> test-id get-entity :id))))
    (testing "Update data"
      (is (true? (update-entity mock-update-entity))))
    (testing "Remove data"
      (is (true? (remove-entity id-to-remove))))))
