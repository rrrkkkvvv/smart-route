import { useState } from "react";
import type {
  Route,
  RouteSearchRequest,
} from "../../features/routes/types/route.types";
import RouteSearchForm from "../../features/routes/components/RouteSearchForm";
import { routeApi } from "../../features/routes/services/routeApi";
import RouteResults from "../../features/routes/components/RouteResults";
import RouteMap from "../map/components/RouteMap";
import type { Location } from "../../features/locations/types/location.types";
import { useNavigate } from "react-router-dom";
import { CiLogout } from "react-icons/ci";
import { useAuth } from "../../features/hooks/useAuth";

const PlannerLayout = () => {
  const [routes, setRoutes] = useState<Route[]>([]);
  const { logout } = useAuth();
  const [selectedRoute, setSelectedRoute] = useState<Route | null>(null);
  const navigate = useNavigate();

  const [searchLocations, setSearchLocations] = useState<{
    origin: Location;
    destination: Location;
  } | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const handleLogout = () => {
    logout();
    navigate("/auth", { replace: true });
  };
  const handleSearch = async (request: RouteSearchRequest) => {
    setIsLoading(true);
    setError(null);
    setRoutes([]);
    setSelectedRoute(null);
    setSearchLocations({
      origin: request.origin,
      destination: request.destination,
    });

    try {
      const results = await routeApi.search(request);
      console.log(results);
      setRoutes(results);

      if (results.length > 0) {
        setSelectedRoute(results[0]);
      }
    } catch (err: any) {
      console.error("Route search failed:", err);
      setError("Unable to find routes. Please try again.");
    } finally {
      setIsLoading(false);
    }
  };
  const handleSelectRoute = (route: Route) => {
    setSelectedRoute(route);
  };
  return (
    <div className="min-h-screen bg-slate-50 md:flex">
      {/* <PlannerSidebar /> */}

      <div className="flex min-w-0 flex-1 flex-col">
        <main className="flex-1 p-4 sm:p-6 lg:p-8">
          <div className="mb-6">
            <h1 className="text-2xl font-semibold tracking-tight text-slate-900">
              Plan your journey
            </h1>
            <p className="mt-1 text-sm text-slate-500">
              Find and compare routes for your next trip.
            </p>
            <button
              type="button"
              onClick={handleLogout}
              className="inline-flex
               shrink-0 items-center gap-2 rounded-lg border
                border-slate-200 bg-white px-3 py-2 text-sm 
                font-medium text-slate-600 shadow-sm transition
                 hover:border-red-200 hover:bg-red-50 hover:text-red-600 focus:outline-none focus:ring-2 focus:ring-red-500/20"
            >
              <CiLogout size={16} />
              <span>Logout</span>
            </button>
          </div>

          <div className="grid min-h-150 grid-cols-1 gap-6 xl:grid-cols-[380px_minmax(0,1fr)]">
            <section className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
              <h2 className="text-base font-semibold text-slate-900">
                Route planner
              </h2>

              <p className="mt-1 text-sm text-slate-500">
                Choose your departure and destination.
              </p>
              {/* {lastSearch && (
                <div className="mt-5 rounded-xl bg-slate-50 p-3">
                  <p className="text-xs font-medium text-slate-500">
                    Last search request
                  </p>

                  <p className="mt-2 text-sm text-slate-800">
                    {lastSearch.origin.name} → {lastSearch.destination.name}
                  </p>

                  <p className="mt-1 text-xs text-slate-500">
                    Coordinates: {lastSearch.origin.coordinate.latitude},{" "}
                    {lastSearch.origin.coordinate.longitude}
                  </p>

                  <p className="mt-1 text-xs capitalize text-slate-500">
                    Transport: {lastSearch.transportModes}
                  </p>
                </div>
              )} */}
              <RouteSearchForm onSearch={handleSearch} />
              {error && (
                <div className="mt-4 rounded-xl border border-red-100 bg-red-50 p-3">
                  <p className="text-sm text-red-600">{error}</p>
                </div>
              )}

              {isLoading && (
                <div className="mt-6 flex items-center justify-center gap-3 py-6">
                  <div className="h-5 w-5 animate-spin rounded-full border-2 border-blue-600 border-t-transparent" />

                  <p className="text-sm text-slate-500">
                    Searching for routes...
                  </p>
                </div>
              )}

              {!isLoading && routes.length > 0 && (
                <RouteResults
                  routes={routes}
                  selectedRoute={selectedRoute}
                  onSelect={handleSelectRoute}
                />
              )}
              {!isLoading &&
                !error &&
                routes.length === 0 &&
                searchLocations && (
                  <div className="mt-6 rounded-xl border border-slate-200 bg-slate-50 p-4 text-center">
                    <p className="text-sm font-medium text-slate-700">
                      No routes found
                    </p>
                    <p className="mt-1 text-xs text-slate-500">
                      Try different locations or transportation modes.
                    </p>
                  </div>
                )}
            </section>

            <section className="relative min-h-125 overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
              <RouteMap
                origin={searchLocations?.origin ?? null}
                destination={searchLocations?.destination ?? null}
                selectedRoute={selectedRoute}
              />
            </section>
          </div>
        </main>
      </div>
    </div>
  );
};

export default PlannerLayout;
