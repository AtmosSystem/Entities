(in-ns 'atmos-entities.core)


(defn- add-persist-entity*
  [entity]
  (insert entities
          (values entity)))

(defn- add-entity*
  [entity]
  (if-let [key-inserted (add-persist-entity* entity)]
    (:generated_key key-inserted)
    false))