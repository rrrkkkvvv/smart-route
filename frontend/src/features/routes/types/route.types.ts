import type { Coordinate } from "../../../shared/types/coordinate.types";
import type { Location } from "../../locations/types/location.types";


export type RouteProfile = "CAR" | "WALK" | "BIKE" | "TRANSIT";

export interface RouteSearchRequest {
  origin: Location;
  destination: Location;
  profiles: RouteProfile[];
}

export interface RoutePoint {
  name: string;
  coordinate: Coordinate;
}

export interface RouteApiRequest {
  origin: RoutePoint;
  destination: RoutePoint;
  profiles: RouteProfile[];
}

export interface RouteSummary {
  distance: number;
  duration: number;
}

export interface RoadRoute {
  summary: RouteSummary;
  profile: RouteProfile;
  geometry: string;
}

export interface TransitStop {
  name: string;
  coordinate: Coordinate;
}

export type LegTransportMode =
  | "WALK"
  | "BUS"
  | "TRAM"
  | "RAIL"
  | "SUBWAY"
  | "TRAIN"
  | "FERRY";

export interface TransitLeg {
  mode: LegTransportMode;
  geometry: string;
  from: RoutePoint;
  to: RoutePoint;
  stops: TransitStop[];
  routeName: string | null;
}

export interface TransitRoute {
  summary: RouteSummary;
  profile: "TRANSIT";
  legs: TransitLeg[];
}

export type Route = RoadRoute | TransitRoute;


