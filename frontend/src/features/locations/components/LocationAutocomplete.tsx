import { useEffect, useState } from "react";
import { locationApi } from "../services/locationApi";
import type { Location } from "../types/location.types";

interface LocationAutocompleteProps {
  id: string;
  label: string;
  placeholder: string;
  value: Location | null;
  onChange: (location: Location | null) => void;
  disabled?: boolean;
}

const LocationAutocomplete = ({
  id,
  label,
  placeholder,
  value,
  onChange,
  disabled = false,
}: LocationAutocompleteProps) => {
  const [query, setQuery] = useState(value?.name ?? "");
  const [suggestions, setSuggestions] = useState<Location[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    if (value || query.trim().length < 3) {
      setSuggestions([]);
      setIsLoading(false);
      setError(null);
      return;
    }

    const controller = new AbortController();

    const timeoutId = setTimeout(async () => {
      setIsLoading(true);
      setError(null);

      try {
        const results = await locationApi.search(
          query.trim(),
          controller.signal,
        );
        console.log(results);
        if (!controller.signal.aborted) {
          console.log("true");

          setSuggestions(results);
          setIsOpen(true);
        }
      } catch {
        if (!controller.signal.aborted) {
          setError("Unable to search locations. Try again.");
          setSuggestions([]);
        }
      } finally {
        if (!controller.signal.aborted) {
          setIsLoading(false);
        }
      }
    }, 400);

    return () => {
      clearTimeout(timeoutId);
      controller.abort();
    };
  }, [query, value]);

  useEffect(() => {
    if (value) {
      setQuery(value.name);
    }
  }, [value]);
  const handleInputChange = (newQuery: string) => {
    setQuery(newQuery);
    onChange(null);
    setError(null);
    setIsOpen(true);
  };

  const handleSelect = (location: Location) => {
    setQuery(location.name);
    onChange(location);
    setSuggestions([]);
    setIsOpen(false);
  };

  return (
    <div className="relative">
      <label
        htmlFor={id}
        className="mb-2 block text-sm font-medium text-slate-700"
      >
        {label}
      </label>

      <input
        id={id}
        type="text"
        value={query}
        onChange={(event) => handleInputChange(event.target.value)}
        onFocus={() => {
          if (!value && suggestions.length > 0) {
            setIsOpen(true);
          }
        }}
        onBlur={() => {
          setTimeout(() => setIsOpen(false), 150);
        }}
        placeholder={placeholder}
        disabled={disabled}
        autoComplete="off"
        className="h-12 w-full rounded-xl border border-slate-200 bg-white px-4 text-sm text-slate-900 outline-none transition placeholder:text-slate-400 hover:border-slate-300 focus:border-blue-500 focus:ring-4 focus:ring-blue-500/10 disabled:bg-slate-50"
      />

      {isOpen && !value && query.trim().length >= 3 && (
        <div className="absolute left-0 right-0 top-full z-30 mt-2 overflow-hidden rounded-xl border border-slate-200 bg-white shadow-lg">
          {isLoading && (
            <div className="px-4 py-3 text-sm text-slate-500">
              Searching locations...
            </div>
          )}

          {!isLoading && error && (
            <div className="px-4 py-3 text-sm text-red-600">{error}</div>
          )}

          {!isLoading && !error && suggestions.length === 0 && (
            <div className="px-4 py-3 text-sm text-slate-500">
              No locations found.
            </div>
          )}

          {!isLoading && suggestions.length > 0 && (
            <ul className="max-h-60 overflow-y-auto py-1">
              {suggestions.map((location) => (
                <li
                  key={`${location.coordinate.latitude}-${location.coordinate.longitude}-${location.name}`}
                >
                  <button
                    type="button"
                    onMouseDown={(event) => event.preventDefault()}
                    onClick={() => handleSelect(location)}
                    className="w-full px-4 py-3 text-left transition hover:bg-slate-50"
                  >
                    <span className="block text-sm font-medium text-slate-800">
                      {location.name}
                    </span>

                    <span className="mt-1 block text-xs text-slate-400">
                      {location.coordinate.latitude.toFixed(4)},{" "}
                      {location.coordinate.longitude.toFixed(4)}
                    </span>
                  </button>
                </li>
              ))}
            </ul>
          )}
        </div>
      )}

      {query.trim().length > 0 && query.trim().length < 3 && !value && (
        <p className="mt-2 text-xs text-slate-400">
          Enter at least 3 characters.
        </p>
      )}
    </div>
  );
};

export default LocationAutocomplete;
