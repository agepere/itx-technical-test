package com.itx.technicalTest.infrastructure.repositories;

import com.itx.technicalTest.infrastructure.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoProductRepository extends MongoRepository<ProductEntity, String> {


    @Aggregation(pipeline = {
            """
                    {$addFields: 
                        {
                             stockLines: {
                                 $filter: {
                                     input: "$stockLines",
                                     as: "item",
                                     cond: {
                                         $ne: ["$$item.stock", 0]
                                     },
                                 },
                             },
                         }
                     }
                     """,
            """
                    {$addFields: 
                        {totalScore: 
                            {
                                $add: [
                                    {$multiply: ["$sales", ?0],},
                                    {$multiply: [{ $size: "$stockLines"}, ?1]},
                                ],
                            },
                        }
                    }
                     """,
            """
                    {$sort: {totalScore: -1 }}
                    """,

    })
    Slice<ProductEntity> findAllSortedByScore(Double salesScoreRatio, Double stockScoreRatio, Pageable pageable);

}
