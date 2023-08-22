package com.itx.technicalTest.infrastructure.adapters.mongodb.aggregations;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;

public class ProductScoreAggregationUtils {

    private static final String SALES_MONGO_FIELD = "sales";
    private static final String STOCK_LINES_MONGO_FIELD = "stockLines";
    private static final String STOCK_LINES_STOCK_MONGO_FIELD = "stock";
    private static final String TOTAL_SCORE_AGGREGATION_FIELD = "totalScore";

    private ProductScoreAggregationUtils() {
        throw new IllegalStateException("ProductScoreAggregationUtils is an utility class, it should not be instantiated.");
    }

    public static AddFieldsOperation removeStockLinesOutOfStock() {
        AggregationExpression filterRemoveOutOfStock = ComparisonOperators.Ne.valueOf("$$item." + STOCK_LINES_STOCK_MONGO_FIELD).notEqualToValue(0);

        return Aggregation.addFields().addField(STOCK_LINES_MONGO_FIELD).
                withValueOf(ArrayOperators.Filter
                        .filter(STOCK_LINES_MONGO_FIELD)
                        .as("item")
                        .by(filterRemoveOutOfStock))
                .build();
    }


    public static AddFieldsOperation calculateTotalScore(Double salesScoreRatio, Double stockScoreRatio) {
        return Aggregation.addFields().addField(TOTAL_SCORE_AGGREGATION_FIELD).
                withValueOf(ArithmeticOperators.Add
                        .valueOf(getSalesScore(salesScoreRatio))
                        .add(getStockScore(stockScoreRatio)))
                .build();
    }

    private static AggregationExpression getSalesScore(Double ratio) {
        return ArithmeticOperators.Multiply.valueOf("$" + SALES_MONGO_FIELD).multiplyBy(ratio);
    }

    private static AggregationExpression getStockScore(Double ratio) {
        return ArithmeticOperators.Multiply.valueOf(ArrayOperators.Size.lengthOfArray("$" + STOCK_LINES_MONGO_FIELD)).multiplyBy(ratio);
    }

    public static SortOperation sortByTotalScore() {
        return Aggregation.sort(Sort.Direction.DESC, TOTAL_SCORE_AGGREGATION_FIELD);
    }
}

