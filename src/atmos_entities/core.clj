(ns atmos-entities.core
  (:require [atmos-kernel.core :refer [defatmos-record-protocols defatmos-seq-record-protocol]]
            [korma.core :refer :all]
            [atmos-rdb-kernel.core :refer [defadd-entity
                                           defget-entity
                                           defget-identity-entity
                                           defget-all-entity
                                           defupdate-entity
                                           defremove-entity]])
  (:import (clojure.lang PersistentArrayMap PersistentVector)))

(defatmos-record-protocols :Entity :Repository)
(defatmos-seq-record-protocol :Entity :entities :Repository)

(defatmos-record-protocols :Contact :Repository)
(defatmos-seq-record-protocol :Contact :contacts :Repository)



(load "implementation")