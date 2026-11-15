import { httpClient } from "../../../shared/api/htttpClient";
import type { Location } from "../types/location.types";

export const locationApi = {
  async search(query: string, signal?: AbortSignal): Promise<Location[]> {
    const response = await httpClient.get<Location[]>("/locations/", {
      params: { query },
      signal,
    });

    return response.data;
  },
};
