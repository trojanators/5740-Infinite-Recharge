package frc.robot.util;

/*
 * PID.java
 * A PID feedback controller.
 * @author Julia Cecchetti
 */

public class PID{
	double m_p;   // P coefficient
	double m_i;   // I coefficient
	double m_d;   // D coefficient

	double m_desiredValue; // Desired value
	double m_previousValue; // Value at last call
	int m_errorSum; // Sum of previous errors (for I calculation)
	int m_errorIncrement; // Max increment to error sum each call
	double m_errorEpsilon; // Allowable error in determining when done
	
	boolean m_firstCycle; // Flag for first cycle
	double m_maxOutput; // Ceiling on calculation output

	int m_minCycleCount; // Minimum number of cycles in epsilon range to be done
	int m_cycleCount; // Current number of cycles in epsilon range

/**
 * Initializes the PID object. All parameters default to 0.
 */
public PID (double p, double i, double d, double epsilon){
	m_p = p;
	m_i = i;
	m_d = d;

	m_errorEpsilon = epsilon;
	m_desiredValue = 0; // Default to 0, set later by the user
	m_firstCycle = true;
	m_maxOutput = 1; // Default to full range
	m_errorIncrement = 1;

	m_cycleCount = 0;
	m_minCycleCount = 10; // Default
}

/**
 * Sets the PID constants to new values.
 */
public void setConstants(double p, double i, double d)
{
	m_p = p;
	m_i = i;
	m_d = d;
	
}

/**
 * Sets the allowable error range away from the desired value.
 */
public void setErrorEpsilon(int epsilon)
{
	m_errorEpsilon = epsilon;
}

/**
 * Sets the maximum increment to the error sum used in the I component
 * calculation.
 * This defaults to 1 in the constructor, which has worked well for 1114 the
 * past few years.
 */
public void setErrorIncrement(int inc)
{
	m_errorIncrement = inc;
}

/**
 * Sets the desired value.
 */
public void setDesiredValue(double val)
{
	m_desiredValue = val;
}

/**
 * Sets the ceiling for the output of the calculation.
 * This defaults to 1.0 (full output). Values should be between 0.0 and 1.0.
 */
public void setMaxOutput(double max)
{	if(max >= 0.0 && max <= 1.0)
	{
		m_maxOutput = max;
	}
}

/**
 * Resets the error sum back to zero.
 */
public void resetErrorSum()
{
	m_errorSum = 0;
}

/**
 * Calculates the PID output based on the current value.
 * PID constants and desired value should be set before calling this
 * function.
 */
public double calcPID(double currentValue)
{	
	// Initialize all components to 0.0 to start.
	double pVal = 0;
	double iVal = 0;
	double dVal = 0;
		
	// Don't apply D the first time through.
	if(m_firstCycle)
	{	
		m_previousValue = currentValue;  // Effective velocity of 0
		m_firstCycle = false;
	}
	
	// Calculate P Component.
	double error = m_desiredValue - currentValue;
	pVal = m_p * (double)error;
	
	// Calculate I Component.
	// Error is positive and outside the epsilon band.
	if(error >= m_errorEpsilon)
	{	
		// Check if epsilon was pushing in the wrong direction.
		if(m_errorSum < 0)
		{
			// If we are fighting away from the point, reset the error.
			m_errorSum = 0;
		}
		if(error < m_errorIncrement)
		{
			// If the error is smaller than the max increment amount, add it.
			m_errorSum += error;
		}
		else 
		{
			// Otherwise, add the maximum increment per cycle.
			m_errorSum += m_errorIncrement;      
		}
	}
	// Error is negative and outside the epsilon band.
	else if(error <= -m_errorEpsilon)
	{	
		if(m_errorSum > 0)
		{
			// If we are fighting away from the point, reset the error.
			m_errorSum = 0;
		}
		// error is small than max contribution -> just subtract error amount
		if(error > -m_errorIncrement)
		{
			// If the error is smaller than the max increment amount, add it.
			m_errorSum += error; // Error is negative
		}
		else
		{
			// Otherwise, subtract the maximum increment per cycle.
			m_errorSum -= m_errorIncrement;
		}
	}
	// Error is inside the epsilon band. 
	else
	{
		m_errorSum = 0;
	}
	iVal = m_i * (double)m_errorSum;
	
	// Calculate D Component.
	double velocity = currentValue - m_previousValue;
	dVal = m_d * (double)velocity;

	// Calculate and limit the ouput: Output = P + I - D
	double output = pVal + iVal - dVal;
	if(output > m_maxOutput)
	{
		output = m_maxOutput;
	}
	else if(output < -m_maxOutput)
	{
		output = -m_maxOutput;
	}
	
	// Save the current value for next cycle's D calculation.
	m_previousValue = currentValue;
	
	return output;
}

/**
 * Sets the minimum number of cycles the value must be in the epsilon range
 * before the system is considered stable.
 */
public void setMinDoneCycles(int n)
{
	m_minCycleCount = n;
}

/**
 * Returns true if the last input was within the epsilon range of the
 * destination value, and the system is stable.
 */
public boolean isDone()
{	
	
	if (m_previousValue <= m_desiredValue + m_errorEpsilon
			&& m_previousValue >= m_desiredValue - m_errorEpsilon
			&& !m_firstCycle)
	{
		if(m_cycleCount >= m_minCycleCount)
		{

			m_cycleCount = 0;
			
			return true;
		
		}
		else 
		{	
			m_cycleCount++;
		}
	}
	
	//m_cycleCount = 0;		
	
	return false;
}
}