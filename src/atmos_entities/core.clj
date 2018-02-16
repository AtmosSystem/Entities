(ns atmos-entities.core
  (:require [atmos-kernel.core :refer [defatmos-record-protocols]]
            [korma.db :as sql]
            [korma.core :refer :all])
  (:import (clojure.lang PersistentArrayMap PersistentVector)))

(defatmos-record-protocols :Entity :Repository)

(defprotocol IEntitySeqRepository
  (get-entities [data])
  (remove-entities [data]))


(load "implementation")