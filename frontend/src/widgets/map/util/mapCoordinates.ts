import polyline from "@mapbox/polyline";
import * as L from "leaflet";

const POLYLINE_PRECISION = 5;

export const decodeGeometry = (geometry: string): L.LatLng[] => {
  const coordinates = polyline.decode(geometry, POLYLINE_PRECISION);

  return coordinates.map(([latitude, longitude]) =>
    L.latLng(latitude, longitude),
  );
};
