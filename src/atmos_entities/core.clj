(ns atmos-entities.core
  (:require [atmos-kernel.core :refer [defatmos-record-protocols defatmos-seq-record-protocol]]
            [korma.core :refer :all])
  (:import (clojure.lang PersistentArrayMap PersistentVector)))

(defatmos-record-protocols :Entity :Repository)

(defatmos-seq-record-protocol :Entity :entities :Repository)



(load "implementation")