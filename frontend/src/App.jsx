import Gift from "./page/Gift/Gift";
import {createBrowserRouter, Navigate, RouterProvider} from "react-router-dom";
import RootLayout from "./page/RootLayout";

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout/>,
    children: [
      {index: true, element: <Navigate to='/gifts' replace />},
      {path: 'gifts', element: <Gift/>}
    ]
  }
]);

function App() {
  return <RouterProvider router={router}/>;
}

export default App;
