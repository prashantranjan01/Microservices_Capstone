import {
  Box,
  Button,
  FormLabel,
  FormControl,
  TextField,
  Typography,
  Stack,
  Card as MuiCard,
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { useForm, Controller } from 'react-hook-form';
import { useNavigate } from 'react-router';
import { authService } from '../../../services/authServices';
import type { User } from '../../../types/User';
import { useAuth } from '../../../context/AuthContext';

const Card = styled(MuiCard)(({ theme }) => ({
  display: 'flex',
  flexDirection: 'column',
  alignSelf: 'center',
  width: '100%',
  padding: theme.spacing(4),
  gap: theme.spacing(2),
  margin: 'auto',
  [theme.breakpoints.up('sm')]: {
    maxWidth: '500px',
  },
  boxShadow:
    'hsla(220, 30%, 5%, 0.05) 0px 5px 15px 0px, hsla(220, 25%, 10%, 0.05) 0px 15px 35px -5px',
  ...theme.applyStyles('dark', {
    boxShadow:
      'hsla(220, 30%, 5%, 0.5) 0px 5px 15px 0px, hsla(220, 25%, 10%, 0.08) 0px 15px 35px -5px',
  }),
}));

const RegisterContainer = styled(Stack)(({ theme }) => ({
  height: 'calc((1 - var(--template-frame-height, 0)) * 100dvh)',
  minHeight: '100%',
  padding: theme.spacing(2),
  [theme.breakpoints.up('sm')]: {
    padding: theme.spacing(4),
  },
  '&::before': {
    content: '""',
    display: 'block',
    position: 'absolute',
    zIndex: -1,
    inset: 0,
    backgroundImage:
      'radial-gradient(ellipse at 50% 50%, hsl(210, 100%, 97%), hsl(0, 0%, 100%))',
    backgroundRepeat: 'no-repeat',
    ...theme.applyStyles('dark', {
      backgroundImage:
        'radial-gradient(at 50% 50%, hsla(210, 100%, 16%, 0.5), hsl(220, 30%, 5%))',
    }),
  },
}));

type FormValues = {
  firstName: string;
  lastName: string;
  email: string;
  username: string;
  password: string;
};

export default function Register() {
  const { control, handleSubmit, formState: { errors } } = useForm<FormValues>();
  const navigate = useNavigate();
  const { login } = useAuth();

  const onSubmit = async (data: FormValues) => {
    try {
      const res = await authService.register(data.firstName , data.lastName , data.email , data.username , data.password);
      if (res && res.token) {
        login(res.user as User, res.token);
        navigate('/');
      } 
    } catch (err) {
      console.error('Registration failed:', err);
    }
  };

  return (
    <RegisterContainer direction="column" justifyContent="center">
      <Card variant="outlined">
        <Typography
          component="h1"
          variant="h4"
          sx={{ width: '100%', fontSize: 'clamp(2rem, 10vw, 2.15rem)' }}
        >
          Register
        </Typography>
        <Box
          component="form"
          onSubmit={handleSubmit(onSubmit)}
          sx={{
            display: 'flex',
            flexDirection: 'column',
            width: '100%',
            gap: 2,
          }}
        >
          {[
            { name: 'firstName', label: 'First Name' },
            { name: 'lastName', label: 'Last Name' },
            { name: 'email', label: 'Email', type: 'email' },
            { name: 'username', label: 'Username' },
            { name: 'password', label: 'Password', type: 'password' },
          ].map(({ name, label, type = 'text' }) => (
            <FormControl key={name}>
              <FormLabel htmlFor={name}>{label}</FormLabel>
              <Controller
                name={name as keyof FormValues}
                control={control}
                defaultValue=""
                rules={{
                  required: `${label} is required`,
                  ...(name === 'email' && {
                    pattern: {
                      value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                      message: 'Enter a valid email',
                    },
                  }),
                  ...(name === 'password' && {
                    minLength: {
                      value: 6,
                      message: 'Password must be at least 6 characters long',
                    },
                  }),
                }}
                render={({ field }) => (
                  <TextField
                    {...field}
                    id={name}
                    type={type}
                    placeholder={label}
                    fullWidth
                    variant="outlined"
                    error={!!errors[name as keyof FormValues]}
                    helperText={errors[name as keyof FormValues]?.message}
                  />
                )}
              />
            </FormControl>
          ))}

          <Button type="submit" fullWidth variant="contained">
            Register
          </Button>

          <Button
            fullWidth
            variant="outlined"
            onClick={() => navigate('/auth')}
          >
            Go to Login
          </Button>
        </Box>
      </Card>
    </RegisterContainer>
  );
}
