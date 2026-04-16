// CRUXTRACK: SHAPE OF JSON RETURNED BY /API/AUTH/ME AND /API/AUTH/LOGIN
export interface UserInfo {
  username: string;
  roles: string[];
  primaryRole: string;
}
