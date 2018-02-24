(ns atmos-entities.core-test
  (:require [clojure.test :refer :all]
            [atmos-entities.core :refer :all]
            [atmos-kernel.core :refer [read-resource-edn]]
            [atmos-rdb-kernel.core :refer [defpersistence init-persistence]]))

(def configuration (read-resource-edn :config-dev))

(-> configuration :database defpersistence init-persistence)

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
      (is (number? id-inserted)))
    (testing "Retrieve single data"
      (is (= test-id (-> test-id get-entity :id))))
    (testing "Retrieve multiple data"
      (is (= 2 (count (get-entities [6 13])))))
    (testing "Update data"
      (is (true? (update-entity mock-update-entity))))
    (testing "Remove data"
      (is (true? (remove-entity id-to-remove))))))
