import { httpClient } from "../../../shared/api/htttpClient";
import type { Route, RouteSearchRequest } from "../types/route.types";

export const routeApi = {
  async search(request: RouteSearchRequest): Promise<Route[]> {
    const response = await httpClient.post<Route[]>("/routes/search", request);

    return response.data;
  },
};
