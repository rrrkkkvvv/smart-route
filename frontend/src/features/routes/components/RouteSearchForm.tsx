import { useState, type FormEvent } from "react";
import type { RouteProfile, RouteSearchRequest } from "../types/route.types";
import type { Location } from "../../locations/types/location.types";
import LocationAutocomplete from "../../locations/components/LocationAutocomplete";
import { TbArrowsDownUp } from "react-icons/tb";

interface RouteSearchFormProps {
  onSearch: (request: RouteSearchRequest) => void;
  isLoading?: boolean;
}
interface TransportOption {
  value: RouteProfile;
  label: string;
  description: string;
}
const transportOptions: TransportOption[] = [
  {
    value: "CAR",
    label: "Car",
    description: "Driving",
  },
  {
    value: "WALK",
    label: "Walk",
    description: "Walking",
  },
  {
    value: "BIKE",
    label: "Bike",
    description: "Cycling",
  },
  {
    value: "TRANSIT",
    label: "Transit",
    description: "Public transport",
  },
];

const RouteSearchForm = ({
  onSearch,
  isLoading = false,
}: RouteSearchFormProps) => {
  const [origin, setOrigin] = useState<Location | null>(null);
  const [destination, setDestination] = useState<Location | null>(null);
  const [routeProfiles, setRouteProfiles] = useState<RouteProfile[]>(["CAR"]);

  const handleSwap = () => {
    setOrigin(destination);
    setDestination(origin);
  };

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!origin || !destination) {
      return;
    }

    onSearch({
      origin,
      destination,
      profiles: routeProfiles,
    });
  };
  const handleClickOption = (profile: RouteProfile) => {
    if (routeProfiles.includes(profile)) {
      setRouteProfiles(routeProfiles.filter((m) => m !== profile));
    } else {
      setRouteProfiles([...routeProfiles, profile]);
    }
  };
  return (
    <form onSubmit={handleSubmit} className="mt-6">
      <div className="relative space-y-3">
        <div>
          <div className="relative">
            <span className="absolute left-4 top-1/2 h-2.5 w-2.5 -translate-y-1/2 rounded-full border-2 border-blue-600" />

            <LocationAutocomplete
              id="origin"
              label="From"
              placeholder="Enter starting location"
              value={origin}
              onChange={setOrigin}
              disabled={isLoading}
            />
          </div>
        </div>

        <div className="relative flex justify-end  z-10">
          <button
            type="button"
            onClick={handleSwap}
            disabled={isLoading}
            aria-label="Swap origin and destination"
            title="Swap locations"
            className=" right-0 flex h-9 w-9 items-center justify-center rounded-lg border border-slate-200 bg-white text-slate-500 shadow-sm transition hover:border-blue-200 hover:bg-blue-50 hover:text-blue-600 disabled:cursor-not-allowed disabled:opacity-50"
          >
            <TbArrowsDownUp />
          </button>
        </div>

        <div>
          <div className="relative">
            <span className="absolute left-4 top-1/2 h-2.5 w-2.5 -translate-y-1/2 rounded-full border-2 border-rose-500" />

            <LocationAutocomplete
              id="destination"
              label="To"
              placeholder="Enter destination"
              value={destination}
              onChange={setDestination}
              disabled={isLoading}
            />
          </div>
        </div>
      </div>

      <div className="mt-7">
        <h3 className="mb-3 text-sm font-medium text-slate-700">
          Transport mode
        </h3>

        <div className="grid grid-cols-2 gap-2">
          {transportOptions.map((option) => {
            const isSelected = routeProfiles.includes(option.value);

            return (
              <button
                key={option.value}
                type="button"
                onClick={() => handleClickOption(option.value)}
                disabled={isLoading}
                aria-pressed={isSelected}
                className={`rounded-xl border p-3 text-left transition ${
                  isSelected
                    ? "border-blue-500 bg-blue-50 ring-2 ring-blue-500/10"
                    : "border-slate-200 bg-white hover:border-slate-300 hover:bg-slate-50"
                } disabled:cursor-not-allowed disabled:opacity-50`}
              >
                <span
                  className={`block text-sm font-medium ${
                    isSelected ? "text-blue-700" : "text-slate-800"
                  }`}
                >
                  {option.label}
                </span>

                <span className="mt-1 block text-xs text-slate-500">
                  {option.description}
                </span>
              </button>
            );
          })}
        </div>
      </div>

      <button
        type="submit"
        disabled={
          isLoading || !origin || !destination || routeProfiles.length == 0
        }
        className="mt-7 flex h-12 w-full items-center justify-center gap-2 rounded-xl bg-blue-600 px-4 text-sm font-semibold text-white shadow-sm shadow-blue-600/20 transition hover:bg-blue-700 focus:outline-none focus:ring-4 focus:ring-blue-500/20 disabled:cursor-not-allowed disabled:bg-slate-300 disabled:shadow-none"
      >
        {isLoading ? "Searching routes..." : "Find routes"}
      </button>
    </form>
  );
};

export default RouteSearchForm;
