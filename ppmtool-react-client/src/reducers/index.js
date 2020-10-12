import { combineReducers } from "redux";
import backLogReducer from "./backLogReducer";
import errorReducer from "./errorReducer";
import projectReducer from "./projectReducer";

export default combineReducers({
  errors: errorReducer,
  project: projectReducer,
  backlog: backLogReducer,
});
