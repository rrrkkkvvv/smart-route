import { useEffect, useRef } from "react";
import * as L from "leaflet";

import type { Location } from "../../../features/locations/types/location.types";
import type {
  Route,
  TransitStop,
} from "../../../features/routes/types/route.types";
import { decodeGeometry } from "../util/mapCoordinates";

interface RouteMapProps {
  origin: Location | null;
  destination: Location | null;
  selectedRoute: Route | null;
}

const TRANSIT_LEG_COLORS = [
  "#2563eb", // blue
  "#16a34a", // green
  "#9333ea", // Purple
  "#ea580c", // Orange
  "#0891b2", // Cyan
  "#db2777", // Pink
  "#ca8a04", // Yellow
  "#4f46e5", // Indigo
];

const ROAD_ROUTE_COLOR = "#2563eb";

const RouteMap = ({ origin, destination, selectedRoute }: RouteMapProps) => {
  const mapElementRef = useRef<HTMLDivElement | null>(null);
  const mapRef = useRef<L.Map | null>(null);
  const layersRef = useRef<L.LayerGroup | null>(null);

  useEffect(() => {
    if (!mapElementRef.current || mapRef.current) {
      return;
    }

    const map = L.map(mapElementRef.current).setView([52.2297, 21.0122], 6);

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
      maxZoom: 19,
    }).addTo(map);

    const layers = L.layerGroup().addTo(map);

    mapRef.current = map;
    layersRef.current = layers;

    return () => {
      map.remove();

      mapRef.current = null;
      layersRef.current = null;
    };
  }, []);

  useEffect(() => {
    const map = mapRef.current;
    const layers = layersRef.current;

    if (!map || !layers) {
      return;
    }

    layers.clearLayers();

    const bounds = L.latLngBounds([]);

    if (origin) {
      const point = L.latLng(
        origin.coordinate.latitude,
        origin.coordinate.longitude,
      );

      L.marker(point).bindTooltip(`From: ${origin.name}`).addTo(layers);

      bounds.extend(point);
    }

    if (destination) {
      const point = L.latLng(
        destination.coordinate.latitude,
        destination.coordinate.longitude,
      );

      L.marker(point).bindTooltip(`To: ${destination.name}`).addTo(layers);

      bounds.extend(point);
    }

    if (selectedRoute) {
      if ("geometry" in selectedRoute) {
        drawRoadRoute(selectedRoute.geometry, layers, bounds);
      } else {
        selectedRoute.legs.forEach((leg, index) => {
          const color =
            leg.mode === "WALK"
              ? "#9ca3af"
              : TRANSIT_LEG_COLORS[index % TRANSIT_LEG_COLORS.length];

          drawTransitLeg(
            leg.geometry,
            leg.mode,
            leg.routeName,
            leg.stops,
            color,
            layers,
            bounds,
          );
        });
      }
    }

    if (bounds.isValid()) {
      map.fitBounds(bounds, {
        padding: [40, 40],
        maxZoom: 15,
      });
    }
  }, [origin, destination, selectedRoute]);

  const drawRoadRoute = (
    geometry: string,
    layers: L.LayerGroup,
    bounds: L.LatLngBounds,
  ) => {
    if (!geometry) {
      return;
    }

    const coordinates = decodeGeometry(geometry);

    if (coordinates.length === 0) {
      return;
    }

    const routeLine = L.polyline(coordinates, {
      color: ROAD_ROUTE_COLOR,
      weight: 5,
      opacity: 0.85,
      lineJoin: "round",
    }).addTo(layers);

    bounds.extend(routeLine.getBounds());
  };

  const addTransitStops = (
    stops: TransitStop[],
    color: string,
    layers: L.LayerGroup,
    bounds: L.LatLngBounds,
  ) => {
    stops.forEach((stop) => {
      const point = L.latLng(
        stop.coordinate.latitude,
        stop.coordinate.longitude,
      );

      L.circleMarker(point, {
        radius: 5,
        color,
        fillColor: "#ffffff",
        fillOpacity: 1,
        weight: 2,
      })
        .bindTooltip(stop.name, {
          direction: "top",
          offset: [0, -5],
        })
        .addTo(layers);

      bounds.extend(point);
    });
  };

  const drawTransitLeg = (
    geometry: string,
    mode: string,
    routeName: string | null,
    stops: TransitStop[],
    color: string,
    layers: L.LayerGroup,
    bounds: L.LatLngBounds,
  ) => {
    addTransitStops(stops, color, layers, bounds);

    if (!geometry) {
      return;
    }

    const coordinates = decodeGeometry(geometry);

    if (coordinates.length === 0) {
      return;
    }

    const legLine = L.polyline(coordinates, {
      color,
      weight: 5,
      opacity: 0.9,
      lineJoin: "round",
    })
      .bindTooltip(`${mode}${routeName ? ` · ${routeName}` : ""}`)
      .addTo(layers);

    bounds.extend(legLine.getBounds());
  };

  return <div ref={mapElementRef} className="z-0 h-150 w-full" />;
};

export default RouteMap;
