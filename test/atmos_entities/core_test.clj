(ns atmos-entities.core-test
  (:require [clojure.test :refer :all]
            [atmos-entities.service :refer :all]))

(deftest repository-testing
  (let [mock-entity {:entities {:type     "MOCK-USER"
                                :name     "Mock"
                                :lastName "User"}}
        test-id 6
        mock-update-entity {:entities {:id       test-id
                                       :lastName "User edited"}}
        id-inserted (add-entities* mock-entity)]
    (testing "Insert data"
      (is (number? id-inserted)))
    (testing "Retrieve single data"
      (is (= test-id (-> test-id get-entities* :id))))
    (testing "Retrieve multiple data"
      (is (= 2 (count (get-entities* {:ids [6 13]})))))
    (testing "Update data"
      (is (true? (update-entities* mock-update-entity))))))
