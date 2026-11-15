import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import AuthPage from "../../pages/auth/AuthPage";
import PlannerPage from "../../pages/planner/PlannerPage";
import NotFoundPage from "../../pages/not-found/NotFoundPage";
import ProtectedRoute from "./ProtectedRoute";
import RestrictedRoute from "./RestrictedRoute";

const AppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<RestrictedRoute />}>
          <Route path="/auth" element={<AuthPage />} />
        </Route>
        <Route element={<ProtectedRoute />}>
          <Route path="/planner" element={<PlannerPage />} />
        </Route>
        <Route path="/" element={<Navigate to="/planner" replace />} />

        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
