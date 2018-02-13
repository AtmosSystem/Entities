(ns atmos-entities.core
  (:require [atmos-kernel.core :refer [defatmos-record-protocol]]))

(defrecord Entity [id type name lastName])

(defatmos-record-protocol Entity :Repository)



