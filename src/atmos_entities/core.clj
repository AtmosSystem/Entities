(ns atmos-entities.core
  (:require [atmos-kernel.protocol :refer [defatmos-record-protocols
                                           defatmos-seq-record-protocol]]))

(declare IEntityBasicProtocol IEntityIdentityProtocol IEntitySeqProtocol
         add-entity update-entity get-entity remove-entity get-entities remove-entities)

(declare IContactBasicProtocol IContactIdentityProtocol IContactSeqProtocol
         add-contact update-contact get-contact remove-contact get-contacts remove-contacts)

(defatmos-record-protocols :Entity)
(defatmos-seq-record-protocol :Entity :entities)

(defatmos-record-protocols :Contact)
(defatmos-seq-record-protocol :Contact :contacts)
