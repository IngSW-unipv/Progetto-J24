package packagefactory;
import order.*;

import packagestrategy.*;

public class PackageStrategyFactory {
	public static PackageStrategy getPackageStrategy(String strategyType) {
			return new PackageStrategy();
    }
}

