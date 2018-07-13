(in-ns 'atmos-entities.core)


(defn- add-entities*
  [entity]
  (if-let [key-inserted (insert entities
                                (values entity))]
    (:generated_key key-inserted)
    false))
