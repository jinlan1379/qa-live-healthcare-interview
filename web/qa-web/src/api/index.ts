import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export interface DoctorDTO {
  id: number;
  username: string;
  name: string;
  title: string;
  department: string;
  avatar: string;
  experience: string;
  specialties: string[];
  isActive: boolean;
}

export interface LoginResponse {
  success: boolean;
  message: string;
  user: DoctorDTO | null;
}

export const doctorApi = {
  getAll: () => api.get<DoctorDTO[]>('/api/doctors'),
  getActive: () => api.get<DoctorDTO[]>('/api/doctors/active'),
  getByUsername: (username: string) => api.get<DoctorDTO>(`/api/doctors/${username}`),
  login: (username: string, password: string) =>
    api.post<LoginResponse>('/api/doctors/login', { username, password }),
};

export const patientApi = {
  login: (username: string, password: string) =>
    api.post<LoginResponse>('/api/patients/login', { username, password }),
  register: (data: { username: string; password: string; name: string; gender?: string; phone?: string }) =>
    api.post<LoginResponse>('/api/patients/register', data),
};

export default api;
