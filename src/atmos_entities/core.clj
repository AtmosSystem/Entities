(ns atmos-entities.core)

(defrecord Entity [id type name last-name])

(defprotocol EntityRepository
  (add-entity [entity])
  (update-entity [entity])
  (remove-entity [entity])
  (get-entity [id]))


(defprotocol EntityApi)
