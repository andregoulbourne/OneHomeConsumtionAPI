package com.andre.utility;

import com.andre.model.Property;

import java.util.function.Predicate;

public class Filter {

    public Predicate<Property> filter() {
        Predicate<Property> isInPriceRange = obj -> obj.getPrice() <= (int) Constants.PropertyCriteria.PRICE_MAX.getValue();
        Predicate<Property> isInDaysOnMarketRange = obj -> obj.getDaysOnMarket() <= (int) Constants.PropertyCriteria.DAYS_ON_MARKET_MAX.getValue();
        Predicate<Property> isActive = obj -> Constants.PropertyCriteria.ACTIVE_STATUS.getValue().equals(obj.getStandardStatus());

        return filter(isInDaysOnMarketRange, isActive)
                .and(isInPriceRange);
    }

    public Predicate<Property> filter14Days() {
        Predicate<Property> isInPriceRange = obj -> obj.getPrice() <= (int) Constants.PropertyCriteria.PRICE_MAX.getValue();
        Predicate<Property> isInDaysOnMarketRange = obj -> obj.getDaysOnMarket() <= 14;
        Predicate<Property> isActive = obj -> Constants.PropertyCriteria.ACTIVE_STATUS.getValue().equals(obj.getStandardStatus());

        return filter(isInDaysOnMarketRange, isActive)
                .and(isInPriceRange);
    }

    public Predicate<Property> filterSold() {
        Predicate<Property> isInDaysOnMarketRange = obj -> obj.getDaysOnMarket() <= (int) Constants.PropertyCriteria.DAYS_ON_MARKET_MAX.getValue();
        Predicate<Property> isActive = obj -> !Constants.PropertyCriteria.ACTIVE_STATUS.getValue().equals(obj.getStandardStatus());

        return filter(isInDaysOnMarketRange, isActive);
    }

    private Predicate<Property> filter(Predicate<Property> isInDaysOnMarketRange, Predicate<Property> isActive) {
        return filterCommonAttributeForReport()
                .and(isInDaysOnMarketRange)
                .and(isActive);
    }

    private Predicate<Property> filterCommonAttributeForReport() {
        Predicate<Property> hasMinBeds = obj -> obj.getBeds() > (int) Constants.PropertyCriteria.BED_MIN.getValue();
        Predicate<Property> isInSquareFootageRange = obj -> obj.getSquareFootage() <= (int) Constants.PropertyCriteria.SQUARE_FOOTAGE_MAX.getValue();
        Predicate<Property> isInYearlyTaxRange = obj -> obj.getTax() <= (Double) Constants.PropertyCriteria.YEARLY_TAX_MAX.getValue();
        Predicate<Property> hasDesiredSiding = obj -> hasDesiredSiding(obj.getContructionMaterial());

        return hasMinBeds
                .and(isInSquareFootageRange)
                .and(isInYearlyTaxRange)
                .and(hasDesiredSiding);
    }

    /*
	private boolean isInDesiredZip(String address) {
		String[] neighborHoods = {"14609", "14613", "14621", "14620", "14606", "14607", "14608", "14611"};

		return isInDesired(neighborHoods, address);
	}
	*/

    private boolean hasDesiredSiding(String buildMaterial) {
        String[] sidings = {"Composite", "Aluminum", "Steel", "Vinyl"};

        return isInDesired(sidings, buildMaterial);
    }

    private boolean isInDesired(String[] desired, String input) {
        for(String desiredEntry : desired) {
            if(input.contains(desiredEntry))
                return true;
        }

        return false;
    }
}
