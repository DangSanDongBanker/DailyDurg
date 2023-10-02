import React, { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";

const MainPage = lazy(() => import("pages/MainPage")); // lazy=동적 import
const ErrorPage = lazy(() => import("pages/ErrorPage"));

const baseRoute = process.env.REACT_APP_BASE_ROUTE
  ? process.env.REACT_APP_BASE_ROUTE
  : "";

const routes = createBrowserRouter([
  {
    path: `${baseRoute}/`,
    element: <MainPage />,
    errorElement: <ErrorPage />,
  },
  {
    path: `${baseRoute}/error`,
    element: <ErrorPage />,
  },
]);

export default routes;
