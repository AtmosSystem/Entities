(in-ns 'atmos-entities.core)


(defadd-entity :entities [entity] #(insert entities
                                           (values entity)))