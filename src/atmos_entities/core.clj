(ns atmos-entities.core
  (:require [atmos-kernel.core :refer [defatmos-record-protocols]]))

(defatmos-record-protocols :Entity :Repository)