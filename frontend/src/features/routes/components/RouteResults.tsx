import { useEffect, useState } from "react";

import { FaCarAlt, FaRegClock } from "react-icons/fa";
import { MdOutlineDirectionsBike } from "react-icons/md";
import { FaWalking } from "react-icons/fa";
import { FaBusAlt } from "react-icons/fa";
import { CiRoute } from "react-icons/ci";
import { HiMiniArrowSmallRight } from "react-icons/hi2";

import type { Route } from "../types/route.types";
import { formatDistance, formatDuration } from "../utils/summaryUtils";

interface RouteResultsProps {
  routes: Route[];
  selectedRoute: Route | null;
  onSelect: (route: Route) => void;
}

type RouteProfile = "CAR" | "BIKE" | "WALK" | "TRANSIT";



const transportOptions = [
  { profile: "CAR", label: "Car", Icon: FaCarAlt },
  { profile: "BIKE", label: "Bike", Icon: MdOutlineDirectionsBike },
  { profile: "WALK", label: "Walk", Icon: FaWalking },
  { profile: "TRANSIT", label: "Transit", Icon: FaBusAlt },
] as const;

const modeLabels: Record<string, string> = {
  WALK: "Walk",
  BUS: "Bus",
  TRAM: "Tram",
  RAIL: "Train",
  SUBWAY: "Metro",
};

const RouteResults = ({
  routes,
  selectedRoute,
  onSelect,
}: RouteResultsProps) => {
  const [activeProfile, setActiveProfile] = useState<RouteProfile>(
    selectedRoute?.profile ?? routes[0]?.profile ?? "CAR",
  );

  useEffect(() => {
    if (selectedRoute) {
      setActiveProfile(selectedRoute.profile);
    } else if (routes.length > 0) {
      setActiveProfile(routes[0].profile);
    }
  }, [selectedRoute, routes]);

  const availableProfiles = new Set(routes.map((route) => route.profile));

  const activeRoutes = routes.filter(
    (route) => route.profile === activeProfile,
  );

  const handleProfileChange = (profile: RouteProfile) => {
    const profileRoutes = routes.filter((route) => route.profile === profile);

    if (profileRoutes.length === 0) return;

    setActiveProfile(profile);

    onSelect(profileRoutes[0]);
  };

  const handleRouteSelect = (route: Route) => {
    onSelect(route);
  };

  if (routes.length === 0) {
    return (
      <div className="mt-5 rounded-xl border border-dashed border-slate-200 p-4 text-center">
        <p className="text-sm text-slate-500">No routes found.</p>
      </div>
    );
  }

  const isTransit = activeProfile === "TRANSIT";

  return (
    <div className="mt-5">
      <div className="grid grid-cols-4 gap-2">
        {transportOptions.map(({ profile, label, Icon }) => {
          const isActive = activeProfile === profile;
          const isAvailable = availableProfiles.has(profile);

          return (
            <button
              key={profile}
              type="button"
              disabled={!isAvailable}
              onClick={() => handleProfileChange(profile)}
              className={`flex flex-col items-center justify-center gap-1.5 rounded-xl border py-2.5 transition ${
                isActive
                  ? "border-blue-500 bg-blue-50 text-blue-600"
                  : "border-slate-200 bg-white text-slate-500 hover:border-slate-300 hover:bg-slate-50"
              } ${
                !isAvailable
                  ? "cursor-not-allowed opacity-35"
                  : "cursor-pointer"
              }`}
              aria-label={label}
              aria-pressed={isActive}
              title={label}
            >
              <Icon size={20} strokeWidth={1.8} />
              <span className="text-[11px] font-medium">{label}</span>
            </button>
          );
        })}
      </div>


      <div className="mt-3">
        {activeRoutes.length === 0 ? (
          <p className="rounded-lg bg-slate-50 p-3 text-center text-xs text-slate-500">
            No routes available for this transport mode.
          </p>
        ) : isTransit ? (
          <div className="space-y-2">
            <div className="flex items-center justify-between">
              <h3 className="text-xs font-semibold text-slate-700">
                Available transit routes
              </h3>
              <span className="text-xs text-slate-400">
                {activeRoutes.length} options
              </span>
            </div>

            {activeRoutes.map((route, index) => {
              if (!("legs" in route)) return null;

              const isSelected = selectedRoute === route;

              return (
                <button
                  key={`transit-${index}`}
                  type="button"
                  onClick={() => handleRouteSelect(route)}
                  className={`w-full rounded-xl border p-3 text-left transition ${
                    isSelected
                      ? "border-blue-500 bg-blue-50/50 ring-2 ring-blue-500/10"
                      : "border-slate-200 bg-white hover:border-slate-300"
                  }`}
                >
                  <div className="flex items-center justify-between">
                    <span className="text-base font-semibold text-slate-900">
                      {formatDuration(route.summary.duration)}
                    </span>

                    <span className="text-xs text-slate-500">
                      {formatDistance(route.summary.distance)}
                    </span>
                  </div>

                  <div className="mt-2 flex flex-wrap items-center gap-1.5">
                    {route.legs.map((leg, legIndex) => (
                      <div key={legIndex} className="flex items-center gap-1.5">
                        {legIndex > 0 && (
                          <span className="text-xs text-slate-300">
                            <HiMiniArrowSmallRight />
                          </span>
                        )}

                        <span className="rounded-md bg-slate-100 px-2 py-1 text-[10px] font-medium text-slate-600">
                          {modeLabels[leg.mode] ?? leg.mode}
                          {leg.routeName && ` ${leg.routeName}`}
                        </span>
                      </div>
                    ))}
                  </div>

                  <div className="mt-2 flex items-center justify-between text-[11px] text-slate-400">
                    <span>{route.legs.length} journey segments</span>

                    {isSelected && (
                      <span className="font-medium text-blue-600">
                        Selected
                      </span>
                    )}
                  </div>
                </button>
              );
            })}
          </div>
        ) : (
          <div className="rounded-xl border border-slate-200 bg-white p-3">
            {activeRoutes.map((route, index) => {
              const isSelected = selectedRoute === route;

              return (
                <button
                  key={`${route.profile}-${index}`}
                  type="button"
                  onClick={() => handleRouteSelect(route)}
                  className="flex w-full items-center justify-between gap-3 text-left"
                >
                  <div>
                    <div className="flex items-center gap-2">
                      <FaRegClock size={16} className="text-slate-400" />

                      <span className="text-lg font-semibold text-slate-900">
                        {formatDuration(route.summary.duration)}
                      </span>
                    </div>

                    <div className="mt-1 flex items-center gap-2 text-xs text-slate-500">
                      <CiRoute size={13} />
                      {formatDistance(route.summary.distance)}
                      <span>·</span>
                      Direct route
                    </div>
                  </div>

                  {isSelected && (
                    <span className="text-xs font-medium text-blue-600">
                      Selected
                    </span>
                  )}
                </button>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
};

export default RouteResults;
